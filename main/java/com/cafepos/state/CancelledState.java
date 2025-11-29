package com.cafepos.state;

import com.cafepos.state.OrderFSM;

final class CancelledState implements State {

    @Override public void pay(OrderFSM ctx) { System.out.println("[State] Cancelled"); }
        @Override public void prepare(OrderFSM ctx) {
            System.out.println("[State] Cancelled"); }
        @Override public void markReady(OrderFSM ctx) {
            System.out.println("[State] Cancelled"); }
        @Override public void deliver(OrderFSM ctx) {
            System.out.println("[State] Cancelled"); }
        @Override public void cancel(OrderFSM ctx) {
            System.out.println("[State] Already cancelled"); }
        @Override public String name() { return "CANCELLED"; }
    }
