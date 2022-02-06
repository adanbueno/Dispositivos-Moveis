import { Router } from 'express';
import UserController from '../controllers/UserController';
import ensureAuthenticated from '../middlewares/ensureAuthenticated';

// http://localhost:3333/users
const usersRouter = Router();

usersRouter.post('/', UserController.store);
usersRouter.put('/', ensureAuthenticated, UserController.update);
usersRouter.get('/', ensureAuthenticated, UserController.index);

export default usersRouter;
