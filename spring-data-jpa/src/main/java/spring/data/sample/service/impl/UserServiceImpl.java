package spring.data.sample.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.data.sample.entity.User;
import spring.data.sample.repositories.UserRepository;
import spring.data.sample.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public User getUser(String id) {
        return userRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        userRepository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(User user) {
        return userRepository.update(user.getId(), user.getName(), user.getAge(), user.getAddress());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<User> list(String name) {
        if (StringUtils.isEmpty(name)) {
            return (List<User>) userRepository.findAll();
        }
        return userRepository.findByNameIsLike("%" + name + "%");
    }

}
