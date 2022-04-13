package v1_16_5R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.misc.SessionIdentifier;

public class SessionHolder extends SessionIdentifier {
    private static SessionHolder holder;
    protected String session;

    protected static void setHolder(SessionHolder holder) {
        SessionHolder.holder = holder;
    }

    @Override
    public String getSession() {
        return session;
    }

    public static SessionHolder getInstance() {
        if (holder == null) {
            holder = new SessionHolder();
        }
        return holder;
    }

    public static void setSession(String session) {
        String sessionI = getInstance().getSession();
        if (sessionI == null) {
            getInstance().session = session;
        }
    }

}
