import { Router } from 'express';
import UserAdminController from '../controllers/UserAdminController';
import ensureAuthenticated from '../middlewares/ensureAuthenticated';

// http://localhost:3333/useradmins
const userAdminsRouter = Router();

userAdminsRouter.post('/', UserAdminController.store);
userAdminsRouter.put('/', ensureAuthenticated, UserAdminController.update);

export default userAdminsRouter;
