import mongoose from 'mongoose';
import mongooseSequence from 'mongoose-sequence';
import PointSchema from './utils/PointSchema';
import passwordEncrypt from '../utils/passwordEncrypt';

const AutoIncremente = mongooseSequence(mongoose);

const PartnersSchema = mongoose.Schema({
  email: {
    type: String,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  registration_store: {
    type: Number,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
  address: {
    type: String,
  },
  number: {
    type: Number,
  },
  location: {
    type: PointSchema,
    index: '2dsphere',
  },
  telephone: String,
  balance: {
    type: Number,
    default: 0,
  },
});

PartnersSchema.plugin(AutoIncremente, {
  inc_field: 'registration_store',
  start_seq: 100000,
  inc_amount: 10,
});

PartnersSchema.pre('save', async function save(next) {
  const partners = this;
  partners.balance = 0;
  partners.password = await passwordEncrypt.encryptPassword(partners.password);
  next();
});

PartnersSchema.pre('findOneAndUpdate', async function update(next) {
  const partnersUpdate = this._update;

  if (partnersUpdate.oldPassword) {
    partnersUpdate.password = await passwordEncrypt.encryptPassword(
      partnersUpdate.password
    );
  }
  next();
});

PartnersSchema.methods = {
  comparePassword(candidatePassword) {
    return passwordEncrypt.compare(candidatePassword, this.password);
  },
};

export default mongoose.model('Partners', PartnersSchema);
