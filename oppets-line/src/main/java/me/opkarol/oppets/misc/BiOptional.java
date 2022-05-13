package me.opkarol.oppets.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class BiOptional<A, B> {
    private final Optional<A> first;
    private Optional<B> second;

    public BiOptional(Optional<A> first, Optional<B> second){
        this.first = first;
        this.second = second;
    }

    public Optional<A> getFirst() {
        return first;
    }

    public Optional<B> getSecond() {
        return second;
    }

    public boolean firstPresent() {
        return first.isPresent();
    }

    public boolean secondPresent() {
        return second.isPresent();
    }

    public boolean bothPresent() {
        return first.isPresent() && second.isPresent();
    }

    public boolean atLeastOnePresent() {
        return first.isPresent() || second.isPresent();
    }

    public BiOptional<A, B> ifFirstPresent(Consumer<? super A> ifPresent){
        if (!second.isPresent()) {
            first.ifPresent(ifPresent);
        }
        return this;
    }

    public BiOptional<A, B> ifSecondPresent(Consumer<? super B> ifPresent){
        if (!first.isPresent()) {
            second.ifPresent(ifPresent);
        }
        return this;
    }

    public BiOptional<A, B> ifBothPresent(BiConsumer<? super A, ? super B> ifPresent){
        if(first.isPresent() && second.isPresent()){
            ifPresent.accept(first.get(), second.get());
        }
        return this;
    }

    public <T extends Throwable> void orElseThrow(Supplier<? extends T> exProvider) throws T{
        if(!first.isPresent() && !second.isPresent()){
            throw exProvider.get();
        }
    }

    public static <A, B> @NotNull BiOptional<A, B> empty() {
        return of(null, null);
    }

    public static <A, B> @NotNull BiOptional<A, B> of(A f, B s) {
        return new BiOptional<>(Optional.ofNullable(f), Optional.ofNullable(s));
    }

    public void setSecond(B second) {
        this.second = Optional.ofNullable(second);
    }
}
