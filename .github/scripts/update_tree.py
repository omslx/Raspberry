#!/usr/bin/env python3
"""Regenerates the "Project structure" block in README.md from `git ls-files`.

The block lives between <!-- TREE:START --> and <!-- TREE:END -->.
Run locally:  python3 .github/scripts/update_tree.py
CI runs it on every push (see .github/workflows/update-readme-tree.yml),
so the tree can never drift from the real repository.
"""
import re
import subprocess
from pathlib import Path

README = Path("README.md")
START, END = "<!-- TREE:START -->", "<!-- TREE:END -->"

# path -> short description (edit freely; unknown files are listed without one)
DESC = {
    "src/main/kotlin/Main.kt": "Entry point: loads config, boots Ktor/Netty",
    "src/main/kotlin/api/routing.kt": "HTTP API: health, jobs, console, power, files, backups",
    "src/main/kotlin/api/CoreToken.kt": "X-Core-Token resolution + constant-time compare",
    "src/main/kotlin/api/RateLimiter.kt": "Fixed-window per-IP limiter (jobs / console / files)",
    "src/main/kotlin/config/ConfigMain.kt": "Typed config model (AppConfig and sections)",
    "src/main/kotlin/config/DefaultConfig.kt": "Default config.toml written on first start",
    "src/main/kotlin/discord/DiscordAlert.kt": "Discord embed alert helper (currently unused)",
    "src/main/kotlin/incus/IncusCli.kt": "Thin wrapper around the `incus` CLI",
    "src/main/kotlin/incus/IncusLauncher.kt": "Runs each server in its own unprivileged container",
    "src/main/kotlin/job/JobModels.kt": "Job contract: settings, parser, supported software",
    "src/main/kotlin/job/JobConsumer.kt": "Redis subscriber with reconnect/backoff",
    "src/main/kotlin/managers/ConfigManager.kt": "Loads/creates config.toml",
    "src/main/kotlin/managers/CoreRuntime.kt": "Singletons, startup, state reconciliation",
    "src/main/kotlin/managers/RedisManager.kt": "Wires publisher, events and job consumer",
    "src/main/kotlin/redis/EventPublisher.kt": "server_build_* / server_power_* events",
    "src/main/kotlin/redis/RedisGetter.kt": "Pub/Sub subscriber callbacks",
    "src/main/kotlin/redis/RedisPublisher.kt": "Fire-and-forget Redis publisher",
    "src/main/kotlin/server/ConsoleRouter.kt": "Log tailer: ring buffer + online-player tracking",
    "src/main/kotlin/server/ForgeInstaller.kt": "One-time Forge --installServer + JVM args",
    "src/main/kotlin/server/PortAllocator.kt": "Persistent random port allocation",
    "src/main/kotlin/server/ProcessLauncher.kt": "Dev runtime: plain `java` on the host",
    "src/main/kotlin/server/ProcessRegistry.kt": "Tracks servers, status, metadata, recovery",
    "src/main/kotlin/server/SafePaths.kt": "Path-traversal / extension / subdir guards",
    "src/main/kotlin/server/ServerLauncher.kt": "Runtime abstraction (incus | process)",
    "src/main/kotlin/server/ServerProvisioner.kt": "create / power pipeline end to end",
    "src/main/kotlin/server/SoftwareDownloader.kt": "Resolves + caches jars from official APIs",
    "src/main/kotlin/utils/CreateDirectory.kt": "mkdir -p + touch helper",
    "src/main/kotlin/utils/Logger.kt": "Timestamped console + file logger",
    "src/main/kotlin/utils/stats.kt": "Status enum",
    "CACHE.md": "How the shared server-jar cache works",
    "build.gradle.kts": "Gradle build (Kotlin 2.3, Ktor 3, Jedis, JDA)",
    "gradle.properties": "Gradle properties",
    "settings.gradle.kts": "Project name + toolchain resolver",
    "LICENSE": "GPL-3.0",
}
DIR_DESC = {
    "src/main/kotlin": "Application sources",
    "src/test/kotlin": "Unit tests (JUnit 5)",
    "cache": "Shared vanilla-jar cache (see CACHE.md)",
}
HIDDEN = ("gradle/", ".idea/", "gradlew", "gradlew.bat", ".gitignore", ".github/")


def files():
    out = subprocess.check_output(["git", "ls-files"], text=True).splitlines()
    res, seen_cache = [], False
    for f in sorted(out):
        if f.startswith(HIDDEN) or f in HIDDEN:
            continue
        if f.startswith("cache/"):
            if not seen_cache:
                res.append("cache/")
                seen_cache = True
            continue
        res.append(f)
    return res


def build_tree(paths):
    root = {}
    for p in paths:
        node = root
        is_dir = p.endswith("/")
        parts = p.rstrip("/").split("/")
        for i, part in enumerate(parts):
            last = i == len(parts) - 1
            key = part + ("/" if (not last or is_dir) else "")
            node = node.setdefault(key, {})
    return root


def render(node, prefix="", path=""):
    lines = []
    items = sorted(node.items(), key=lambda kv: (not kv[0].endswith("/"), kv[0].lower()))
    for i, (name, child) in enumerate(items):
        last = i == len(items) - 1
        full = path + name.rstrip("/")
        desc = DESC.get(full) or DIR_DESC.get(full)
        lines.append((prefix + ("└── " if last else "├── ") + name, desc))
        if child:
            lines += render(child, prefix + ("    " if last else "│   "), full + "/")
    return lines


def main():
    rows = [("Raspberry/", "Repository root")] + render(build_tree(files()))
    width = max(len(r[0]) for r in rows) + 2
    body = "\n".join(f"{n.ljust(width)}# {d}" if d else n for n, d in rows)
    block = f"{START}\n```text\n{body}\n```\n{END}"
    text = README.read_text(encoding="utf-8")
    new = re.sub(re.escape(START) + r".*?" + re.escape(END), lambda _: block, text, flags=re.S)
    if new != text:
        README.write_text(new, encoding="utf-8")
        print("README tree updated")
    else:
        print("README tree already up to date")


if __name__ == "__main__":
    main()
