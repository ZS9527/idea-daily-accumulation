package com.test.testidea.service.zjtq.impl;

import com.google.common.base.Strings;
import com.test.testidea.service.zjtq.ZjtqUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/5/5 16:24
 */

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ZjtqUserServiceImpl implements ZjtqUserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void UserCopy() {
        String oldSql = "select organization, position, phone_number from system_user_info";
        Map<String, String> phoneOrganization = new HashMap<>();
        jdbcTemplate.queryForList(oldSql).stream().forEach(stringObjectMap -> {
            String organization = String.valueOf(stringObjectMap.get("organization")).trim();
            String position = String.valueOf(stringObjectMap.get("position")).trim();

            String phone = String.valueOf(stringObjectMap.get("phone_number")).trim();
            if (!Strings.isNullOrEmpty(organization) && (!"null".equals(organization))) {
                if (!Strings.isNullOrEmpty(position) && (!"null".equals(position))) {
                    organization = organization + "-" + position;
                }
                phoneOrganization.put(phone, organization);
            }
        });
        String sqlV3 = "select username, description from system_user";
        String sqlInsert = "update system_user set description = ? where username = ?";
        jdbcTemplate.queryForList(sqlV3).stream().forEach(stringObjectMap -> {
            String phone = String.valueOf(stringObjectMap.get("username")).trim();
            Object description = stringObjectMap.get("description");
            if (description == null) {
                String organization = phoneOrganization.get(phone);
                if (organization != null) {
                    description = organization;
                    String [] sql = {String.valueOf(description), phone};
                    jdbcTemplate.update(sqlInsert, sql);
                }
            }

        });

    }
}
