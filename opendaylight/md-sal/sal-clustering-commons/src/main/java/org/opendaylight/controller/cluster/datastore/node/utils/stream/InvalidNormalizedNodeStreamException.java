/*
 * Copyright (c) 2015 Brocade Communications Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.cluster.datastore.node.utils.stream;

import java.io.IOException;

/**
 * Exception thrown from NormalizedNodeInputStreamReader when the input stream does not contain
 * valid serialized data.
 *
 * @author Thomas Pantelis
 * @deprecated Use {@link org.opendaylight.yangtools.yang.data.codec.binfmt.InvalidNormalizedNodeStreamException}
 *             instead.
 */
@Deprecated(forRemoval = true)
public class InvalidNormalizedNodeStreamException extends IOException {
    private static final long serialVersionUID = 1L;

    public InvalidNormalizedNodeStreamException(final String message) {
        super(message);
    }

    public InvalidNormalizedNodeStreamException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
