import pytest
from unittest.mock import patch, AsyncMock
from app.graph.nodes.location import location_node, haversine_distance
from app.config import settings

def test_haversine_distance():
    # Known distance test
    dist = haversine_distance(37.7937, -122.3965, 37.8044, -122.2711)
    assert 10000 <= dist <= 12000  # Approx 11km

@pytest.mark.asyncio
async def test_location_node_missing_location():
    state = {"severity_estimate": "High"}
    result = await location_node(state)
    assert result["nearby_vets"]["status"] == "LOCATION_UNAVAILABLE"

@pytest.mark.asyncio
async def test_location_node_not_urgent():
    state = {"latitude": 37.7937, "longitude": -122.3965, "severity_estimate": "Low"}
    result = await location_node(state)
    assert result["nearby_vets"]["status"] == "NOT_URGENT"

@pytest.mark.asyncio
@patch("app.graph.nodes.location.httpx.AsyncClient")
async def test_location_node_success(mock_client_class, monkeypatch):
    monkeypatch.setattr(settings, "google_maps_api_key", "mock_key")
    state = {"latitude": 37.7937, "longitude": -122.3965, "severity_estimate": "High"}
    
    from unittest.mock import MagicMock
    mock_response = AsyncMock()
    mock_response.status_code = 200
    mock_response.json = MagicMock(return_value={
        "places": [
            {
                "id": "place_1",
                "displayName": {"text": "Happy Vet"},
                "formattedAddress": "123 Main St",
                "location": {"latitude": 37.7940, "longitude": -122.3960},
                "nationalPhoneNumber": "123-456-7890",
                "googleMapsUri": "https://maps.google.com/place_1",
                "businessStatus": "OPERATIONAL"
            }
        ]
    })
    
    mock_client = AsyncMock()
    mock_client.post.return_value = mock_response
    # Mock context manager
    mock_client_class.return_value.__aenter__.return_value = mock_client
    
    result = await location_node(state)
    
    assert result["nearby_vets"]["status"] == "SUCCESS"
    assert len(result["nearby_vets"]["providers"]) == 1
    p = result["nearby_vets"]["providers"][0]
    assert p["name"] == "Happy Vet"
    assert p["distance_meters"] > 0
    assert p["phone"] == "123-456-7890"

@pytest.mark.asyncio
@patch("app.graph.nodes.location.httpx.AsyncClient")
async def test_location_node_api_error(mock_client_class, monkeypatch):
    monkeypatch.setattr(settings, "google_maps_api_key", "mock_key")
    state = {"latitude": 37.7937, "longitude": -122.3965, "severity_estimate": "Critical"}
    
    mock_response = AsyncMock()
    mock_response.status_code = 403
    mock_response.text = "Forbidden"
    
    mock_client = AsyncMock()
    mock_client.post.return_value = mock_response
    mock_client_class.return_value.__aenter__.return_value = mock_client
    
    result = await location_node(state)
    assert result["nearby_vets"]["status"] == "API_ERROR"
