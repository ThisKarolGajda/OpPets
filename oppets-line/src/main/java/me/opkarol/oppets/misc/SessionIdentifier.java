package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.prestiges.RandomString;
import org.jetbrains.annotations.NotNull;

public class SessionIdentifier {
    private final String session;

    public SessionIdentifier() {
        session = new RandomString().getString(16, RandomString.Mode.ALPHANUMERIC);
    }

    public String getSession() {
        return session;
    }

    public boolean equals(@NotNull SessionIdentifier o) {
        return session.equals(o.getSession());
    }
}
