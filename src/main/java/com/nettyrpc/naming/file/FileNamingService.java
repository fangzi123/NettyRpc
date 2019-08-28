/*
 * Copyright (c) 2019 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nettyrpc.naming.file;

import com.nettyrpc.naming.NamingService;
import com.nettyrpc.naming.RegisterInfo;
import com.nettyrpc.naming.RegistryCenterAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * Fetch service list from File Naming Service
 */
@Slf4j
public class FileNamingService implements NamingService {
    private CountDownLatch latch = new CountDownLatch(1);

    private RegistryCenterAddress registryCenterAddress;

    public FileNamingService(RegistryCenterAddress registryCenterAddress) {
        this.registryCenterAddress = registryCenterAddress;
    }


    @Override
    public void register(RegisterInfo registerInfo) {

    }


}
