import { Router } from 'express';
import PartnersController from '../controllers/PartnersController';
import ensureAuthenticated from '../middlewares/ensureAuthenticated';

// http://localhost:3333/partners
const partnersRouter = Router();

partnersRouter.post('/', PartnersController.store);
partnersRouter.put('/', ensureAuthenticated, PartnersController.update);

export default partnersRouter;
