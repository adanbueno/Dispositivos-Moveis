import express from 'express';
import mongoose from 'mongoose';
import cors from 'cors';
import mongoConfig from './config/userMongodb';
import routes from './routes';

const app = express();

const { user, password } = mongoConfig.mongo;
mongoose.connect(
  `mongodb+srv://${user}:${password}@universityproject.wqlng.mongodb.net/meu-troco?retryWrites=true&w=majority`,
  {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true,
  }
);

app.use(cors()); // Acesso a aplicação do backend
app.use(express.json());
app.use(routes);

app.listen(3333, () => {
  console.log('🚀 Server started on port 3333');
});
