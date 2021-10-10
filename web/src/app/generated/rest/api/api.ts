export * from './authService.service';
import { AuthServiceService } from './authService.service';
export * from './menuService.service';
import { MenuServiceService } from './menuService.service';
export * from './staticDataService.service';
import { StaticDataServiceService } from './staticDataService.service';
export * from './userService.service';
import { UserServiceService } from './userService.service';
export const APIS = [AuthServiceService, MenuServiceService, StaticDataServiceService, UserServiceService];
