import mongoose from 'mongoose';

const VirtualChangeSchema = mongoose.Schema({
  id_store: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Partners',
    required: true,
  },
  cpf: {
    type: String,
    ref: 'User',
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
  validation: {
    type: Boolean,
    default: false,
  },
  created_at: {
    type: Date,
    required: true,
    default: Date.now,
  },
  update_at: {
    type: Date,
    required: true,
    default: Date.now,
  },
});

VirtualChangeSchema.pre('update', async function update(next) {
  const virtualChange = this._update;
  virtualChange.update_at = Date.now;
  next();
});

export default mongoose.model('VirtualChange', VirtualChangeSchema);
