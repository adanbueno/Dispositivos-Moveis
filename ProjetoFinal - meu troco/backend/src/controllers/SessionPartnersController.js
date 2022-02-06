import jwt from 'jsonwebtoken';
import * as Yup from 'yup';
import Partners from '../models/Partners';
import authConfig from '../config/auth';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      registration_store: Yup.number().required(),
      password: Yup.string().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { registration_store, password } = req.body;
    const partners = await Partners.findOne({ registration_store });

    if (!partners || !(await partners.comparePassword(password))) {
      return res.status(401).json({ error: 'Invalid data' });
    }

    const { _id: id, name, balance } = partners;

    const { secret, expiresIn } = authConfig.jwt;

    return res.json({
      partners: {
        id,
        name,
        registration_store,
        balance,
      },
      token: jwt.sign({ id }, secret, {
        expiresIn,
      }),
    });
  },
};
