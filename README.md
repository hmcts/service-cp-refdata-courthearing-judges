# Service: CP Reference Data for Court Hearing Judges


## 🚀 Installation

To get started with this project, you'll need Java and Gradle installed.

### Prerequisites

- ☕️ **Java 21 or later**: Ensure Java is installed and available on your `PATH`.
- ⚙️ **Gradle**: You can install Gradle using your preferred method:

  **macOS (Recommended with Homebrew):**
  ```bash
  brew install gradle
  ```

  **Other Platforms:**
  Visit the [Gradle installation guide](https://gradle.org/install/) for platform-specific instructions.

You can verify installation with:
```bash
java -version
gradle -v
```

## 🔑 Environment Setup for Local Builds

To successfully run local builds (e.g., Gradle tasks that interact with GitHub Packages), you must set the following environment variables:

- `GITHUB_ACTOR` – Your GitHub username.
- `GITHUB_TOKEN` – A Personal Access Token (PAT) with appropriate permissions to Read GitHub Packages.

You can create a PAT (Classic) by following [GitHub's official guide](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic).

> NOTE: If your GitHub organisation requires Single Sign-On (SSO), you must authorize your Personal Access Token (PAT) for use with SSO.  
> See [GitHub's guide on authorizing a PAT for SSO](https://docs.github.com/en/enterprise-cloud@latest/authentication/authenticating-with-saml-single-sign-on/authorizing-a-personal-access-token-for-use-with-saml-single-sign-on) for instructions.

### Recommended Approach for macOS Users (using `direnv`)

If you're on macOS, you can use [direnv](https://direnv.net/) to automatically load these environment variables per project:

1. Install `direnv`:
   ```bash
   brew install direnv
   ```

2. Hook it into your shell (example for bash or zsh):
   ```bash
   echo 'eval "$(direnv hook bash)"' >> ~/.bash_profile
   # or for zsh
   echo 'eval "$(direnv hook zsh)"' >> ~/.zshrc
   ```

3. Create a `.envrc` file at the root of the repository with the following contents:
   ```bash
   export GITHUB_ACTOR=your-github-username
   export GITHUB_TOKEN=your-github-personal-access-token
   ```

4. Allow `direnv` to load:
   ```bash
   direnv allow
   ```

This will ensure your environment is correctly set up every time you enter the project directory.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
