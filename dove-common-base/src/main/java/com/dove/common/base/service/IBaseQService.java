package com.dove.common.base.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description: 基层
 * @Auther: qingruizhu
 * @Date: 2020/4/10 13:49
 */
@Validated
public interface IBaseQService<T extends Object, Q extends Object> {
    List<T> listQ(@NotNull Q q);

    T selectQ(@NotNull Q q);

    int insertQ(@NotNull Q q);

    int updateQ(@NotNull Q q);

    T copyQ(@NotNull Q q);


}
