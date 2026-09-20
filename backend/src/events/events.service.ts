import { Injectable } from '@nestjs/common';

@Injectable()
export class EventsService {
  emit(eventName: string, payload: any) {
    // Basic stub for EventEmitter
  }
}
