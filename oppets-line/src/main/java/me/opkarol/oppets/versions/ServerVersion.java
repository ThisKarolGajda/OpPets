package me.opkarol.oppets.versions;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;

public class ServerVersion {
    private static String version;

    public static String getCurrentVersion() {
        return version;
    }

    public static void setSeverVersion(String version) {
        ServerVersion.version = version;
    }

    public static void setVersion(SERVER_BUILD build) {
        SERVER_VERSION = build;
    }

    public static boolean isCompatible(@NotNull Class<?> clazz) {
        if (clazz.isAnnotationPresent(VersionBuildSupported.class)) {
            VersionBuildSupported versionSupported = clazz.getAnnotation(VersionBuildSupported.class);
            return versionSupported.build().isCompatible();
        }
        return true;
    }

    private static SERVER_BUILD SERVER_VERSION = SERVER_BUILD.C2;

    /*
     * ===============
     * A1 - 1.16-.1
     * A2 - 1.16.2-3
     * A3 - 1.16.4-5
     * ===============
     * B1 - 1.17
     * B2 - 1.17.1
     * =============
     * C1 - 1.18-.1
     * C2 - 1.18.2
     */
    public enum SERVER_BUILD {
        A1(1, "1.16"), A2(2, "1.16.3"), A3(3, "1.16.5"), B1(4, "1.17"), B2(5, "1.17.1"), C1(6, "1.18"), C2(7, "1.18.2");
        private final int i;
        private final String version;

        SERVER_BUILD(int i, String s) {
            this.i = i;
            this.version = s;
        }

        boolean isCompatible() {
            return i >= SERVER_VERSION.i;
        }

        public String getVersion() {
            return version;
        }
    }
}
