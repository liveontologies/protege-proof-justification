package io.github.liveontologies.protege.justification.proof;

/*-
 * #%L
 * Protege Proof Justification Explanation
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2017 Live Ontologies Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;

import org.protege.editor.core.Disposable;
import org.protege.editor.owl.OWLEditorKit;

import io.github.liveontologies.protege.justification.proof.service.JustificationProofPluginLoader;
import io.github.liveontologies.protege.justification.proof.service.JustificationProofService;
import io.github.liveontologies.protege.justification.proof.service.ProofPlugin;

/**
 * Keeps track of the available {@link JustificationProofService} plugins.
 * 
 * @author Pavel Klinov pavel.klinov@uni-ulm.de
 * 
 * @author Yevgeny Kazakov
 */
public class JustificationProofServiceManager implements Disposable {

	private static final String KEY_ = "org.liveontologies.protege.justification.proof.services";

	private final OWLEditorKit kit_;

	private final Collection<JustificationProofService<?>> services_;

	public JustificationProofServiceManager(OWLEditorKit kit) throws Exception {
		this.kit_ = kit;
		this.services_ = new ArrayList<JustificationProofService<?>>();
		JustificationProofPluginLoader loader = new JustificationProofPluginLoader(
				kit_);
		for (ProofPlugin plugin : loader.getPlugins()) {
			JustificationProofService<?> service = plugin.newInstance();
			service.initialise();
			services_.add(service);
		}
	}

	public static synchronized JustificationProofServiceManager get(
			OWLEditorKit editorKit) throws Exception {
		// reuse one instance
		JustificationProofServiceManager m = editorKit.getModelManager()
				.get(KEY_);
		if (m == null) {
			m = new JustificationProofServiceManager(editorKit);
			editorKit.put(KEY_, m);
		}
		return m;
	}

	@Override
	public void dispose() throws Exception {
		for (JustificationProofService<?> service : services_) {
			service.dispose();
		}
	}

	public Collection<JustificationProofService<?>> getServices() {
		return services_;
	}

	public OWLEditorKit getOWLEditorKit() {
		return kit_;
	}

}