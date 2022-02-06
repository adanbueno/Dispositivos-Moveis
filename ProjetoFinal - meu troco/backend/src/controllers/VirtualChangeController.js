import * as Yup from 'yup';
import VirtualChange from '../models/VirtualChange';
import User from '../models/User';
import Partners from '../models/Partners';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      cpf: Yup.string().required(),
      value: Yup.number().positive().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { cpf, value } = req.body;

    const { id: id_store } = req.user_id;

    const partners = await Partners.findById(id_store);
    let balance = partners.balance - value;
    await partners.updateOne({ balance });

    const user = await User.findOne({ cpf });
    if (user) {
      balance = user.balance + value;
      await user.updateOne({ balance });
      Object.assign(req.body, { validation: true });
    }

    Object.assign(req.body, { id_store });
    const virtualChange = await VirtualChange.create(req.body);

    return res.json(virtualChange);
  },
};
