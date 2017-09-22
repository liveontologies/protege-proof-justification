package org.liveontologies.protege.justification.proof.service;

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

import org.eclipse.core.runtime.IExtension;
import org.protege.editor.core.plugin.AbstractProtegePlugin;
import org.protege.editor.owl.OWLEditorKit;

/**
 * Instantiates the plugin which is based on specified prover service
 * 
 * @author Alexander Date: 23/02/2017
 */

public class ProverPlugin extends AbstractProtegePlugin<JustificationProofService> {

	public static final String KEY = "org.liveontologies.protege.justification.proof";
	public static final String ID = "JustificationProofService";

	private final OWLEditorKit editorKit_;

	/**
	 * Constructs plugin object
	 * 
	 * @param extension
	 *            plugin extension
	 */
	public ProverPlugin(OWLEditorKit editorKit, IExtension extension) {
		super(extension);
		editorKit_ = editorKit;
	}

	@Override
	public JustificationProofService newInstance() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		return super.newInstance().setup(editorKit_);
	}
}