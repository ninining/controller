/*
 * Copyright (c) 2016 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.cluster.access.commands;

import com.google.common.annotations.Beta;
import org.opendaylight.controller.cluster.access.concepts.RequestException;

/**
 * A {@link RequestException} indicating that the backend has received a request to create a history which has already
 * been retired.
 *
 * @author Robert Varga
 */
@Beta
public final class DeadHistoryException extends RequestException {
    private static final long serialVersionUID = 1L;

    public DeadHistoryException(final long lastSeenHistory) {
        super("Histories up to " + Long.toUnsignedString(lastSeenHistory) + " are accounted for");
    }

    @Override
    public boolean isRetriable() {
        return false;
    }
}
