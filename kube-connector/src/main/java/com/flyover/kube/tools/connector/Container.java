/**
 * 
 */
package com.flyover.kube.tools.connector;

import java.util.Arrays;

import com.flyover.kube.tools.connector.model.ContainerModel;
import com.flyover.kube.tools.connector.model.EnvModel;
import com.flyover.kube.tools.connector.model.EnvModel.SecretKeyRefModel;
import com.flyover.kube.tools.connector.model.EnvModel.ValueFromModel;
import com.flyover.kube.tools.connector.model.PortModel;
import com.flyover.kube.tools.connector.model.TcpProbeModel;
import com.flyover.kube.tools.connector.model.VolumeMountModel;

/**
 * @author mramach
 *
 */
public class Container {
	
	private ContainerModel model;

	public Container(ContainerModel model) {
		this.model = model;
	}
	
	public String name() {
		return this.model.getName();
	}
	
	public Container name(String name) {
		this.model.setName(name);
		return this;
	}
	
	public Container image(String image) {
		this.model.setImage(image);
		return this;
	}
	
	public Container command(String...command) {
		this.model.setCommand(Arrays.asList(command));
		return this;
	}
	
	public Container args(String...args) {
		this.model.setArgs(Arrays.asList(args));
		return this;
	}
	
	public Container imagePullPolicy(String impagePullPolicy) {
		this.model.setImagePullPolicy(impagePullPolicy);
		return this;
	}
	
	public Container tcpPort(int port) {
		
		PortModel p = new PortModel();
		p.setProtocol("TCP");
		p.setContainerPort(port);
		
		model.getPorts().add(p);
		
		return this;
	}
	
	public Container readinessProbeTcp(int port, int initialDelaySeconds, int periodSeconds, int failureThreshold) {
		
		TcpProbeModel probe = new TcpProbeModel();
		probe.getTcpSocket().setPort(port);
		probe.setInitialDelaySeconds(initialDelaySeconds);
		probe.setPeriodSeconds(periodSeconds);
		probe.setFailureThreshold(failureThreshold);
		
		model.setReadinessProbe(probe);
		
		return this;
		
	}
	
	public Container env(String name, String value) {
		
		EnvModel e = new EnvModel();
		e.setName(name);
		e.setValue(value);
		
		model.getEnv().add(e);
		
		return this;
		
	}
	
	public Container volumeMount(Volume v, String mountPath) {
		
		VolumeMountModel vm = new VolumeMountModel();
		vm.setName(v.name());
		vm.setMountPath(mountPath);
		
		model.getVolumeMounts().add(vm);
		
		return this;
		
	}
	
	public Container env(String name, String key, Secret secret) {
		
		SecretKeyRefModel secretKeyRef = new SecretKeyRefModel();
		secretKeyRef.setKey(key);
		secretKeyRef.setName(secret.metadata().getName());
		
		ValueFromModel valueFrom = new ValueFromModel();
		valueFrom.setSecretKeyRef(secretKeyRef);

		EnvModel e = new EnvModel();
		e.setName(name);
		e.setValueFrom(valueFrom);
		
		model.getEnv().add(e);
		
		return this;
		
	}

	protected ContainerModel model() {
		return this.model;
	}

}
