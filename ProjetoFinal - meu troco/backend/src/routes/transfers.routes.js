import { Router } from 'express';
import TransferController from '../controllers/TransferController';

// http://localhost:3333/transfers
const transfersRouter = Router();

transfersRouter.post('/', TransferController.store);
transfersRouter.get('/', TransferController.index);

export default transfersRouter;
