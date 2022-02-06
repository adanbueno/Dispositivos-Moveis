import * as Yup from 'yup';
import Partners from '../models/Partners';

export default {
  async store(req, res) {
    const schema = Yup.object().shape({
      email: Yup.string().email().required(),
      name: Yup.string().required(),
      password: Yup.string().required().min(6),
      address: Yup.string().required(),
      number: Yup.number().required(),
      latitude: Yup.number().required(),
      longitude: Yup.number().required(),
      telephone: Yup.string().required(),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { latitude, longitude } = req.body;

    const location = {
      type: 'Point',
      coordinates: [longitude, latitude],
    };
    Object.assign(req.body, { location });

    const partners = await Partners.create(req.body);
    Object.assign(partners, { password: undefined });

    return res.json(partners);
  },

  async update(req, res) {
    const schema = Yup.object().shape({
      email: Yup.string().email(),
      name: Yup.string(),
      address: Yup.string(),
      number: Yup.number(),
      telephone: Yup.string(),
      latitude: Yup.number().when('longitude', (longitude, field) =>
        longitude ? field.required() : field
      ),
      longitude: Yup.number().when('latitude', (latitude, field) =>
        latitude ? field.required() : field
      ),
      oldPassword: Yup.string().min(6),
      password: Yup.string()
        .min(6)
        .when('oldPassword', (oldPassword, field) =>
          oldPassword ? field.required() : field
        ),
      confirmPassword: Yup.string().when('password', (password, field) =>
        password ? field.required().oneOf([Yup.ref('password')]) : field
      ),
    });

    if (!(await schema.isValid(req.body))) {
      return res.status(400).json({ error: 'Validation fails' });
    }

    const { email, oldPassword, address } = req.body;
    const { id } = req.user_id;
    let partners = await Partners.findById(id);

    if (address && partners.address !== address) {
      const { latitude, longitude } = req.body;
      const location = {
        type: 'Point',
        coordinates: [longitude, latitude],
      };
      Object.assign(req.body, { location });
    }

    if (email && email !== partners.email) {
      const partnersExist = await Partners.findOne({ email });
      if (partnersExist) {
        return res.status(400).json({ error: 'Email already exists.' });
      }
    }

    if (oldPassword && !(await partners.comparePassword(oldPassword))) {
      return res.status(401).json({ error: 'Password does not match.' });
    }

    delete req.body.balance;
    partners = await Partners.findByIdAndUpdate({ _id: id }, req.body, {
      new: true,
    });

    Object.assign(partners, { password: undefined });

    return res.json(partners);
  },
};
