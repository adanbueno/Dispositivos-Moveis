import { Router } from 'express';
import usersRouter from './users.routes';
import userAdminsRouter from './userAdmins.routes';
import partnersRouter from './partners.routes';
import sessionsRouter from './sessions.routes';

import ensureAuthenticated from '../middlewares/ensureAuthenticated';

import virtualChangesRouter from './virtualChanges.routes';
import adminTransfersRouter from './adminTransfers.routes';
import searchPartnersRouter from './searchPartners.routes';
import transfersRouter from './transfers.routes';
import extractsRouter from './extracts.routes';

const routes = Router();

routes.use('/users', usersRouter);
routes.use('/useradmins', userAdminsRouter);
routes.use('/partners', partnersRouter);

routes.use('/sessions', sessionsRouter);

routes.use(ensureAuthenticated);
routes.use('/searchs', searchPartnersRouter);
routes.use('/virtualchanges', virtualChangesRouter);
routes.use('/admintransfers', adminTransfersRouter);
routes.use('/transfers', transfersRouter);
routes.use('/extracts', extractsRouter);

export default routes;
