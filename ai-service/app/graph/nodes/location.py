import logging
import math
import httpx
from typing import Dict, Any

from app.config import settings
from app.graph.state import TriageGraphState

logger = logging.getLogger(__name__)

def haversine_distance(lat1: float, lon1: float, lat2: float, lon2: float) -> int:
    """Calculate the great circle distance between two points on the earth in meters."""
    R = 6371000  # Radius of earth in meters
    phi1 = math.radians(lat1)
    phi2 = math.radians(lat2)
    delta_phi = math.radians(lat2 - lat1)
    delta_lambda = math.radians(lon2 - lon1)

    a = math.sin(delta_phi / 2.0) ** 2 + \
        math.cos(phi1) * math.cos(phi2) * \
        math.sin(delta_lambda / 2.0) ** 2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

    distance = R * c
    return int(distance)

async def location_node(state: TriageGraphState) -> Dict[str, Any]:
    """
    Location Agent: Discovers nearby veterinary providers using Google Places API.
    Does NOT modify business logic or medical assessment.
    """
    logger.info("Executing location_node for rescue_id=%s", state.get("rescue_id"))

    lat = state.get("latitude")
    lon = state.get("longitude")

    # Validate location
    if lat is None or lon is None:
        return {"nearby_vets": {"status": "LOCATION_UNAVAILABLE", "providers": []}}
    
    try:
        lat = float(lat)
        lon = float(lon)
        if not (-90 <= lat <= 90 and -180 <= lon <= 180):
            return {"nearby_vets": {"status": "LOCATION_UNAVAILABLE", "providers": []}}
        # If coordinates are exactly 0,0 it's usually a missing default
        if lat == 0.0 and lon == 0.0:
            return {"nearby_vets": {"status": "LOCATION_UNAVAILABLE", "providers": []}}
    except (ValueError, TypeError):
        return {"nearby_vets": {"status": "LOCATION_UNAVAILABLE", "providers": []}}

    # Check urgency
    severity = state.get("severity_estimate", "UNKNOWN")
    if severity not in ["High", "Critical"]:
        return {"nearby_vets": {"status": "NOT_URGENT", "providers": []}}

    # Check API Key
    api_key = settings.google_maps_api_key
    if not api_key:
        logger.warning("Google Maps API key is not configured.")
        return {"nearby_vets": {"status": "UNKNOWN — REQUIRES DECISION", "providers": []}}

    url = "https://places.googleapis.com/v1/places:searchNearby"
    headers = {
        "X-Goog-Api-Key": api_key,
        "X-Goog-FieldMask": "places.id,places.displayName,places.formattedAddress,places.location,places.nationalPhoneNumber,places.googleMapsUri,places.businessStatus",
        "Content-Type": "application/json"
    }

    # Configurable limits
    radius = 10000.0  # meters
    max_results = 5

    payload = {
        "includedTypes": ["veterinary_care"],
        "maxResultCount": max_results,
        "locationRestriction": {
            "circle": {
                "center": {
                    "latitude": lat,
                    "longitude": lon
                },
                "radius": radius
            }
        },
        "rankPreference": "DISTANCE"
    }

    try:
        async with httpx.AsyncClient(timeout=10.0) as client:
            response = await client.post(url, headers=headers, json=payload)
            
            if response.status_code != 200:
                logger.error(f"Google Places API error {response.status_code}: {response.text}")
                return {"nearby_vets": {"status": "API_ERROR", "providers": []}}
            
            data = response.json()
            places = data.get("places", [])
            
            if not places:
                return {"nearby_vets": {"status": "NO_NEARBY_VETS_FOUND", "providers": []}}
            
            providers = []
            for p in places:
                p_lat = p.get("location", {}).get("latitude")
                p_lon = p.get("location", {}).get("longitude")
                
                dist = 0
                if p_lat is not None and p_lon is not None:
                    dist = haversine_distance(lat, lon, p_lat, p_lon)
                
                provider = {
                    "id": p.get("id", ""),
                    "name": p.get("displayName", {}).get("text", "Unknown Clinic"),
                    "address": p.get("formattedAddress", "Unknown Address"),
                    "distance_meters": dist,
                    "phone": p.get("nationalPhoneNumber"),
                    "maps_uri": p.get("googleMapsUri"),
                    "business_status": p.get("businessStatus")
                }
                providers.append(provider)
                
            # Providers are already ranked by distance by Google, but we can re-sort just in case
            providers.sort(key=lambda x: x["distance_meters"])
            
            return {"nearby_vets": {"status": "SUCCESS", "providers": providers}}

    except httpx.TimeoutException:
        logger.error("Google Places API timeout")
        return {"nearby_vets": {"status": "TIMEOUT", "providers": []}}
    except Exception as e:
        logger.exception(f"Unexpected error in location_node: {e}")
        return {"nearby_vets": {"status": "ERROR", "providers": []}}
