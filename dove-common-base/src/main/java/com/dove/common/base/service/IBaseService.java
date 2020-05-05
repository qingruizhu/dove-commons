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
public interface IBaseService<T extends Object> {
    List<T> list(@NotNull T t);

    T select(@NotNull Long primaryKey);

    T select(@NotNull T t);

    int insert(@NotNull T t);

    int update(@NotNull T t);

    int delete(@NotNull Long primaryKey);
}
