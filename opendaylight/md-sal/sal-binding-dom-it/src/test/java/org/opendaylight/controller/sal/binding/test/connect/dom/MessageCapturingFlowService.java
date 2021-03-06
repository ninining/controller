/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.sal.binding.test.connect.dom;

import static org.junit.Assert.assertNotNull;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RoutedRpcRegistration;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.of.migration.test.model.rev150210.KnockKnockInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.of.migration.test.model.rev150210.KnockKnockOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.of.migration.test.model.rev150210.OpendaylightOfMigrationTestModelService;
import org.opendaylight.yangtools.yang.binding.BaseIdentity;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;

public class MessageCapturingFlowService implements OpendaylightOfMigrationTestModelService, AutoCloseable {

    private ListenableFuture<RpcResult<KnockKnockOutput>> knockKnockResult;

    private final Multimap<InstanceIdentifier<?>, KnockKnockInput> receivedKnocks = HashMultimap.create();
    private RoutedRpcRegistration<OpendaylightOfMigrationTestModelService> registration;

    public ListenableFuture<RpcResult<KnockKnockOutput>> getKnockKnockResult() {
        return knockKnockResult;
    }

    public MessageCapturingFlowService setKnockKnockResult(
            final ListenableFuture<RpcResult<KnockKnockOutput>> kkOutput) {
        this.knockKnockResult = kkOutput;
        return this;
    }

    public Multimap<InstanceIdentifier<?>, KnockKnockInput> getReceivedKnocks() {
        return receivedKnocks;
    }

    public MessageCapturingFlowService registerTo(final RpcProviderRegistry registry) {
        registration = registry.addRoutedRpcImplementation(OpendaylightOfMigrationTestModelService.class, this);
        assertNotNull(registration);
        return this;
    }

    @Override
    public void close() {
        registration.close();
    }

    public MessageCapturingFlowService registerPath(final Class<? extends BaseIdentity> context,
            final InstanceIdentifier<?> path) {
        registration.registerPath(context, path);
        return this;
    }

    public MessageCapturingFlowService unregisterPath(final Class<? extends BaseIdentity> context,
            final InstanceIdentifier<?> path) {
        registration.unregisterPath(context, path);
        return this;
    }

    public static MessageCapturingFlowService create() {
        return new MessageCapturingFlowService();
    }

    public static MessageCapturingFlowService create(final RpcProviderRegistry registry) {
        MessageCapturingFlowService ret = new MessageCapturingFlowService();
        ret.registerTo(registry);
        return ret;
    }

    @Override
    public ListenableFuture<RpcResult<KnockKnockOutput>> knockKnock(final KnockKnockInput input) {
        receivedKnocks.put(input.getKnockerId(), input);
        return knockKnockResult;
    }
}
