import mongoose from 'mongoose';
import VirtualChange from './VirtualChange';
import passwordEncrypt from '../utils/passwordEncrypt';

const UserSchema = new mongoose.Schema({
  name: {
    type: String,
    trim: true,
    required: true,
  },
  cpf: {
    type: String,
    trim: true,
    unique: true,
    required: true,
  },
  email: {
    type: String,
    trim: true,
    unique: true,
    required: true,
  },
  password: {
    type: String,
    required: true,
  },
  balance: {
    type: Number,
    default: 0,
  },
});

UserSchema.pre('save', async function save(next) {
  const user = this;

  const transfersCpf = await VirtualChange.find({
    cpf: user.cpf,
    validation: false,
  });

  let balance = 0;
  if (transfersCpf) {
    await transfersCpf.map(async (transf) => {
      balance += transf.value;
      await transf.updateOne({ validation: true });
      return transf.value;
    });
    user.balance = balance;
  }

  user.password = await passwordEncrypt.encryptPassword(user.password);
  next();
});

UserSchema.pre('findOneAndUpdate', async function update(next) {
  const userUpdate = this._update;

  if (userUpdate.oldPassword) {
    userUpdate.password = await passwordEncrypt.encryptPassword(
      userUpdate.password
    );
  }

  next();
});

UserSchema.methods = {
  comparePassword(candidatePassword) {
    return passwordEncrypt.compare(candidatePassword, this.password);
  },
};

export default mongoose.model('User', UserSchema);
