import mongoose from 'mongoose';
import mongooseSequence from 'mongoose-sequence';
import passwordEncrypt from '../utils/passwordEncrypt';

const AutIncremente = mongooseSequence(mongoose);

const UserAdminSchema = mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    unique: true,
    required: true,
  },
  registration: {
    type: Number,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
});

UserAdminSchema.plugin(AutIncremente, {
  inc_field: 'registration',
  start_seq: 1000,
});

UserAdminSchema.pre('save', async function save(next) {
  const user = this;
  delete user.registration;
  user.password = await passwordEncrypt.encryptPassword(user.password);
  next();
});

UserAdminSchema.pre('findOneAndUpdate', async function update(next) {
  const userUpdate = this._update;
  delete userUpdate.registration;

  if (userUpdate.oldPassword) {
    userUpdate.password = await passwordEncrypt.encryptPassword(
      userUpdate.password
    );
  }
  next();
});

UserAdminSchema.methods = {
  comparePassword(candidatePassword) {
    return passwordEncrypt.compare(candidatePassword, this.password);
  },
};

export default mongoose.model('UserAdmin', UserAdminSchema);
