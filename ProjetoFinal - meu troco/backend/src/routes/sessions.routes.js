import { Router } from 'express';
import SessionUserController from '../controllers/SessionUserController';
import SessionUserAdminController from '../controllers/SessionUserAdminController';
import SessionPartnersController from '../controllers/SessionPartnersController';

// http://localhost:3333/sessions
const sessionsRouter = Router();

sessionsRouter.post('/users', SessionUserController.store);
sessionsRouter.post('/useradmins', SessionUserAdminController.store);
sessionsRouter.post('/partners', SessionPartnersController.store);

export default sessionsRouter;
