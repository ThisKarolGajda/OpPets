package me.opkarol.oppets.exceptions;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

public class Exception {
    private final String message;
    private final String clazz;
    private final Throwable cause;
    private final RuntimeException exception;

    public Exception(String message, String clazz, Throwable cause) {
        this.message = message;
        this.clazz = clazz;
        this.cause = cause;
        this.exception = null;
    }

    public Exception(String clazz, Throwable cause, RuntimeException exception) {
        this.message = null;
        this.clazz = clazz;
        this.cause = cause;
        this.exception = exception;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getMessage() {
        return message;
    }

    public String getClazz() {
        return clazz;
    }

    public RuntimeException getException() {
        return exception;
    }
}
