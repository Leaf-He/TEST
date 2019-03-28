package com.app.core.mapper;

import com.app.core.entity.Role;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * Created by lucky on 2017/7/5.
 */

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色
     * @param userId
     * @return
     */
    public List<Role> findByUserId(Long userId);

    public List<Role> findPage(Pagination page, Map<String, Object> params);
}
