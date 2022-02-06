import { Router } from 'express';
import VirtualChangeController from '../controllers/VirtualChangeController';

// http://localhost:3333/virtualchanges
const virtualChangesRouter = Router();

virtualChangesRouter.post('/', VirtualChangeController.store);

export default virtualChangesRouter;
