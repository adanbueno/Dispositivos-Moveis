import { Router } from 'express';
import ExtractsController from '../controllers/ExtractsController';

// http://localhost:3333/extracts
const extractsRouter = Router();

extractsRouter.get('/', ExtractsController.index);

export default extractsRouter;
