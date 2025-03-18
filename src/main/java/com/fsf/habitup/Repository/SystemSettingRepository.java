package com.fsf.habitup.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.fsf.habitup.entity.SystemSetting;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {
    @Override
    @NonNull
    Optional<SystemSetting> findById(@NonNull Long id);

}
