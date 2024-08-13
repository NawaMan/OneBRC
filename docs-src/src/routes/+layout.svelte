<script lang="ts">
	import '$lib/styles/global.css';
	import { page } from '$app/stores';
	import { scaleMode } from '$lib/stores/scaleMode';
	import { onMount } from 'svelte';
	import TableOfContent from '$lib/components/TableOfContent.svelte';
	import SizeMode from '$lib/components/SizeMode.svelte';
	import { browser } from '$app/environment';
	import Copyright from '$lib/components/Copyright.svelte';

	let container: any;
	let content: any;

	let isScaled = false;

	if (browser) {
		scaleMode.subscribe(value => {
			isScaled = value;

			if (isScaled) {
				adjustSize();
				window.addEventListener('resize', adjustSize);
			} else {
				window.removeEventListener('resize', adjustSize);
				if (container) {
					container.style.width  = '1280px';
					container.style.height =  '720px';
					content.style.transform = `scale(1)`;
					content.style.transformOrigin = 'top left';
				}
			}
		});
	}

	function adjustSize() {
		if (!container)
			return;

		const aspectRatio = 1280 / 720;
		const windowWidth = window.innerWidth;
		const windowHeight = window.innerHeight;
		const windowRatio = windowWidth / windowHeight;

		if (windowRatio > aspectRatio) {
			container.style.height = 'calc(100vh - 10px)';
			container.style.width = `${Math.round(container.offsetHeight*aspectRatio)}px`;
		} else {
			container.style.width = 'calc(100vw - 10px)';
			container.style.height = `${Math.round(container.offsetWidth / aspectRatio)}px`;
		}
		let scale = (container.offsetWidth - 10) / 1280.0;
		content.style.transform = `scale(${scale})`;
		content.style.transformOrigin = 'top left';
	}
</script>

<div class="container" class:scale-mode={isScaled} bind:this={container}>
	<div class="content"  bind:this={content}>
		<TableOfContent />
		<SizeMode />
		<slot />
		<Copyright />
	</div>
</div>

<style>
	.container {
		width: 1280px;
		height: 720px;
		margin: 0 auto;
		display: flex;
		background: #112c63;
		border: 5px solid #ccc; /* Optional: adds a border to visualize the container */
		position: relative;
	}
	.container.scale-mode {
		width: 100%;
		height: 100%;
		max-width: 100vw;
		max-height: 100vh;
	}
	.content {
		width: calc(1280px - 20px);
		height: calc(720px - 20px);
		min-width: calc(1280px - 20px);
		min-height: calc(720px - 20px);
		overflow: hidden;
		padding: 10px;
		margin: 0px;
		display: block;
		justify-content: center;
		align-items: center;
		color: #C0F1FF;
		background: #112c63;
		font-family: 'Noto Sans', 'Cormorant Garamond', serif;
		transition: all 0.3s ease;
	}
</style>
