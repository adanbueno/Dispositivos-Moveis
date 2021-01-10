const express = require('express');
const mongoose = require('mongoose');
const { mongo } = require('./config/userMongodb');
const routes = require('./routes');

const app = express();

mongoose.connect(`mongodb+srv://${mongo.NAME}:${mongo.PASSWORD}@universityproject.wqlng.mongodb.net/books?retryWrites=true&w=majority`, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

app.use(express.json());
app.use(routes);

app.listen(3333, () => {
  console.log('🚀 Server started on port 3333');
});
