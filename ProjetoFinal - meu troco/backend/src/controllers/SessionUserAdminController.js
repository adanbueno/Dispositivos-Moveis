import jwt from 'jsonwebtoken';
import * as Yup from 'yup';
import UserAdmin from '../models/UserAdmin';
import authConfig from '../config/auth';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      registration: Yup.number().required(),
      password: Yup.string().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { registration, password } = req.body;
    const userAdmin = await UserAdmin.findOne({ registration });

    if (!userAdmin || !(await userAdmin.comparePassword(password))) {
      return res.status(401).json({ error: 'Invalid data' });
    }

    const { _id: id, name } = userAdmin;

    const { secret, expiresIn } = authConfig.jwt;

    return res.json({
      userAdmin: {
        id,
        name,
        registration,
      },
      token: jwt.sign({ id }, secret, {
        expiresIn,
      }),
    });
  },
};
