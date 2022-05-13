package me.opkarol.oppets.exceptions;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.APIDatabase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ExceptionLogger {
    private static ExceptionLogger exceptionLogger;
    private final Logger logger = APIDatabase.getInstance().getPlugin().getLogger();
    private final List<Exception> list = new ArrayList<>();

    public ExceptionLogger() {
        exceptionLogger = this;
    }

    public void addException(Exception exception) {
        list.add(exception);
        broadcastException(exception);
    }

    public void broadcastException(@NotNull Exception exception) {
        String message = String.format("[OPPETS-ERROR]\nClass: %s\nCause: %s\nMessage: %s", exception.getClazz(), exception.getCause() == null ? "NULL" : exception.getCause(), exception.getMessage() == null ? "NULL" : exception.getMessage());
        logger.warning(message);
    }

    public List<Exception> getList() {
        return list;
    }

    public static ExceptionLogger getInstance() {
        if (exceptionLogger == null) {
            new ExceptionLogger();
        }
        return exceptionLogger;
    }

    public void throwException(String message) {
        try {
            throw new InvalidDefaultException(message);
        } catch (InvalidDatabaseException e) {
            ExceptionLogger.getInstance().addException(new Exception(message, this.getClass().toString(), e.fillInStackTrace()));
        }
    }
}
