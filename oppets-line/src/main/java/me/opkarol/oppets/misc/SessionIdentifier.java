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

/**
 * The type Session identifier.
 */
public class SessionIdentifier {
    /**
     * The Session.
     */
    private final String session;

    /**
     * Instantiates a new Session identifier.
     */
    public SessionIdentifier() {
        session = new RandomString().getString(16, RandomString.Mode.ALPHANUMERIC);
    }

    /**
     * Gets session.
     *
     * @return the session
     */
    public String getSession() {
        return session;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    public boolean equals(@NotNull SessionIdentifier o) {
        return session.equals(o.getSession());
    }
}
