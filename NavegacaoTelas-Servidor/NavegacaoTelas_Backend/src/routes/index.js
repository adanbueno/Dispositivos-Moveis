const { Router } = require('express');
const BookController = require('../controllers/BookController');

const routes = Router();

routes.get('/books', BookController.show);
routes.post('/books', BookController.store);
routes.put('/books/:id', BookController.update);
routes.delete('/books/:id', BookController.destroy);

module.exports = routes;
