import * as Yup from 'yup';
import AdminTransfer from '../models/AdminTransfer';
import Partners from '../models/Partners';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      id_recipient: Yup.string().required(),
      value: Yup.number().positive().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { id } = req.user_id;
    const { id_recipient } = req.body;

    Object.assign(req.body, { id_sender: id });

    const adminTransfer = await AdminTransfer.create(req.body);

    return res.json(adminTransfer);
  },
};
