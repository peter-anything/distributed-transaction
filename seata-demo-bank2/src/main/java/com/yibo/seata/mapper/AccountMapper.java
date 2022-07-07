package com.yibo.seata.mapper;

import com.yibo.seata.domain.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

public interface AccountMapper extends Mapper<Account> {
    @Update("UPDATE `t_account` SET residue = residue - #{money},used = used + #{money} where user_id = #{userId}")
    int decreaseMoney(@Param("userId")Long userId, @Param("money") BigDecimal money);

    @Update("UPDATE `t_account` SET frozen = frozen + #{money}, residue = residue - #{money} WHERE user_id = #{userId}")
    void tryDecreaseMoney(@Param("userId") Long userId, @Param("money") BigDecimal money);

    @Update("UPDATE `t_account` SET frozen = frozen - #{money}, used = used + #{money} WHERE user_id = #{userId}")
    void confirmDecreaseMoney(@Param("userId") Long userId, @Param("money") BigDecimal money);

    @Update("UPDATE `t_account` SET frozen = frozen - #{money}, residue = residue + #{money} WHERE user_id = #{userId}")
    void cancelDecreaseMoney(@Param("userId") Long userId, @Param("money") BigDecimal money);

    @Update("UPDATE `t_account` SET frozen = frozen + #{money}, total = total + #{money} WHERE user_id = #{userId}")
    void tryIncreaseMoney(@Param("userId") Long userId, @Param("money") BigDecimal money);

    @Update("UPDATE `t_account` SET frozen = frozen - #{money}, residue = residue + #{money} WHERE user_id = #{userId}")
    int confirmIncreaseMoney(@Param("userId")Long userId, @Param("money") BigDecimal money);

    @Update("UPDATE `t_account` SET frozen = frozen - #{money}, total = total - #{money} WHERE user_id = #{userId}")
    int cancelIncreaseMoney(@Param("userId")Long userId, @Param("money") BigDecimal money);
}