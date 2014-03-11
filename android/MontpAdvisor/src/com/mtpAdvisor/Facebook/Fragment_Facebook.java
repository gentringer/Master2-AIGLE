/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mtpAdvisor.Facebook;

import java.util.Arrays;

import com.facebook.widget.LoginButton;
import com.example.projet.*;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Facebook extends Fragment {

	private Button skipLoginButton;
	private SkipLoginCallback skipLoginCallback;

	public interface SkipLoginCallback {
		void onSkipLoginPressed();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splash, container, false);

		LoginButton authButton = (LoginButton) view.findViewById(R.id.login_button);
		authButton.setReadPermissions(Arrays.asList("user_location", "user_birthday", "read_friendlists",  "user_likes"));

		skipLoginButton = (Button) view.findViewById(R.id.skip_login_button);
		skipLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (skipLoginCallback != null) {
					skipLoginCallback.onSkipLoginPressed();
				}
			}
		});

		return view;
	}

	public void setSkipLoginCallback(SkipLoginCallback callback) {
		skipLoginCallback = callback;
	}
}
