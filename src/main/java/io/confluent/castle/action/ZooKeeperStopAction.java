/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.confluent.castle.action;

import io.confluent.castle.cluster.CastleCluster;
import io.confluent.castle.cluster.CastleNode;
import io.confluent.castle.common.CastleUtil;
import io.confluent.castle.role.ZooKeeperRole;

/**
 * Stop zookeeper.
 */
public final class ZooKeeperStopAction extends Action {
    public final static String TYPE = "zooKeeperStop";

    public ZooKeeperStopAction(String scope, ZooKeeperRole role) {
        super(new ActionId(TYPE, scope),
            new TargetId[] {
                new TargetId(BrokerStopAction.TYPE)
            },
            new String[] {},
            role.initialDelayMs());
    }

    @Override
    public void call(CastleCluster cluster, CastleNode node) throws Throwable {
        if (node.uplink() == null) {
            node.log().printf("*** Skipping zooKeeperStop, because the node is not running.%n");
            return;
        }
        CastleUtil.killJavaProcess(cluster, node, ZooKeeperRole.ZOOKEEPER_CLASS_NAME, false);
    }
}
