import { Router } from 'express';
import SearchPartnersController from '../controllers/SearchPartnersController';

// http://localhost:3333/searchs
const searchsPartnersRouter = Router();

searchsPartnersRouter.get('/', SearchPartnersController.index);

export default searchsPartnersRouter;
