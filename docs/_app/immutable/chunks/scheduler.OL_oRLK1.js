function w(){}function E(t,n){for(const e in n)t[e]=n[e];return t}function j(t){return t()}function M(){return Object.create(null)}function v(t){t.forEach(j)}function S(t){return typeof t=="function"}function A(t,n){return t!=t?n==n:t!==n||t&&typeof t=="object"||typeof t=="function"}let i;function B(t,n){return t===n?!0:(i||(i=document.createElement("a")),i.href=n,t===i.href)}function C(t){return Object.keys(t).length===0}function y(t,...n){if(t==null){for(const r of n)r(void 0);return w}const e=t.subscribe(...n);return e.unsubscribe?()=>e.unsubscribe():e}function F(t){let n;return y(t,e=>n=e)(),n}function P(t,n,e){t.$$.on_destroy.push(y(n,e))}function U(t,n,e,r){if(t){const o=k(t,n,e,r);return t[0](o)}}function k(t,n,e,r){return t[1]&&r?E(e.ctx.slice(),t[1](r(n))):e.ctx}function G(t,n,e,r){if(t[2]&&r){const o=t[2](r(e));if(n.dirty===void 0)return o;if(typeof o=="object"){const l=[],_=Math.max(n.dirty.length,o.length);for(let s=0;s<_;s+=1)l[s]=n.dirty[s]|o[s];return l}return n.dirty|o}return n.dirty}function H(t,n,e,r,o,l){if(o){const _=k(n,e,r,l);t.p(_,o)}}function I(t){if(t.ctx.length>32){const n=[],e=t.ctx.length/32;for(let r=0;r<e;r++)n[r]=-1;return n}return-1}function J(t){const n={};for(const e in t)e[0]!=="$"&&(n[e]=t[e]);return n}function K(t,n){const e={};n=new Set(n);for(const r in t)!n.has(r)&&r[0]!=="$"&&(e[r]=t[r]);return e}let f;function h(t){f=t}function b(){if(!f)throw new Error("Function called outside component initialization");return f}function L(t){b().$$.on_mount.push(t)}function N(t){b().$$.after_update.push(t)}function Q(t){b().$$.on_destroy.push(t)}function R(t,n){const e=t.$$.callbacks[n.type];e&&e.slice().forEach(r=>r.call(this,n))}const a=[],g=[];let u=[];const m=[],x=Promise.resolve();let p=!1;function q(){p||(p=!0,x.then(z))}function T(){return q(),x}function O(t){u.push(t)}const d=new Set;let c=0;function z(){if(c!==0)return;const t=f;do{try{for(;c<a.length;){const n=a[c];c++,h(n),D(n.$$)}}catch(n){throw a.length=0,c=0,n}for(h(null),a.length=0,c=0;g.length;)g.pop()();for(let n=0;n<u.length;n+=1){const e=u[n];d.has(e)||(d.add(e),e())}u.length=0}while(a.length);for(;m.length;)m.pop()();p=!1,d.clear(),h(t)}function D(t){if(t.fragment!==null){t.update(),v(t.before_update);const n=t.dirty;t.dirty=[-1],t.fragment&&t.fragment.p(t.ctx,n),t.after_update.forEach(O)}}function V(t){const n=[],e=[];u.forEach(r=>t.indexOf(r)===-1?n.push(r):e.push(r)),e.forEach(r=>r()),u=n}export{E as A,K as B,J as C,F as D,G as a,P as b,U as c,Q as d,R as e,B as f,I as g,N as h,S as i,g as j,M as k,z as l,C as m,w as n,L as o,O as p,V as q,v as r,A as s,T as t,H as u,f as v,h as w,j as x,a as y,q as z};
