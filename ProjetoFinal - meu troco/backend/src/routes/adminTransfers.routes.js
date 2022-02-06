import { Router } from 'express';
import AdminTransferController from '../controllers/AdminTransferController';

// http://localhost:3333/admintransfers
const AdminTransferRouter = Router();

AdminTransferRouter.post('/', AdminTransferController.store);

export default AdminTransferRouter;
