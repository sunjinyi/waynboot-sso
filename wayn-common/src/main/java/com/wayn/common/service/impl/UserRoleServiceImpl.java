package com.wayn.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wayn.common.dao.UserRoleDao;
import com.wayn.common.domain.UserRole;
import com.wayn.common.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author wayn
 * @since 2019-04-13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    @Override
    public Set<String> findRolesByUid(String id) {
        //复杂连表
        List<UserRole> list = list(new QueryWrapper<UserRole>().eq("userId", id)
                .select("distinct roleId")
                .exists(" SELECT u.id FROM sys_user u WHERE u.id = userId ")
                .exists("  SELECT r.id FROM sys_role r WHERE r.id = roleId AND r.roleStatus = 1 "));
        return list.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

}
