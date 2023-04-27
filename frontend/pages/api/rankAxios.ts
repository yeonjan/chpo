import { authApi, basicApi } from './customAxio';

/**
 *랭킹 필터 언어 목록 조회 api
 * @param languageType 깃허브인지 백준인지 타입
 * @returns
 */
export const getFilter = async (languageType: String) => {
  const params = {
    type: languageType,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/filter/language',
    params: params,
  });

  return data.data;
};

/**
 * 깃허브 랭킹 조회 api
 * @param sizeParam 개수
 * @param rankParam 시작랭킹
 * @returns
 */
export const getGithubRanking = async (sizeParam: number, rankParam: number) => {
  const params = {
    size: sizeParam,
    rank: rankParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/github/ranks',
    params: params,
  });

  return data.data.ranks;
};

/**
 * 백준 랭킹 조회 api
 * @returns
 */
export const getBojRanking = async () => {
  const { data } = await basicApi({
    method: 'get',
    url: '/boj/ranks',
  });

  return data.data.ranks;
};

/**
 * 내 백준 랭킹 조회 api
 */
export const getMyBojRanking = async () => {
  const { data } = await authApi({
    method: 'get',
    url: '/boj/my-rank',
  });

  return data;
};

/**
 * 내 깃허브 랭킹 조회 api
 * @param languageParam 필터 검색 시 언어
 * @returns
 */
export const getMyGitRanking = async (languageParam?: number) => {
  const params = {
    language: languageParam,
  };
  const { data } = await authApi({
    method: 'get',
    url: '/github/my-rank',
    params: params,
  });

  return data;
};

/**
 * 깃허브 닉네임 검색
 * @param nicknameParam 검색할 닉네임
 */
export const getSearchGitUser = async (nicknameParam: string | undefined) => {
  const params = {
    nickname: nicknameParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/github/search',
    params: params,
  });

  return data;
};

/**
 * 검색 결과 클릭 후 요청하는 깃허브 랭크 정보 api
 * @param userIdParam 유저 아이디
 * @param languageParam 필터 적용시 언어 번호
 * @returns
 */
export const getSearchGitResult = async (userIdParam: number, languageParam?: number) => {
  const params = {
    language: languageParam,
  };

  const { data } = await authApi({
    method: 'get',
    url: `/github/user-rank/${userIdParam}`,
    params: params,
  });

  return data;
};

/**
 * 백준 닉네임 검색
 * @param nicknameParam 검색할 닉네임
 */
export const getSearchBojUser = async (nicknameParam?: string | undefined) => {
  const params = {
    nickname: nicknameParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/boj/search',
    params: params,
  });

  return data;
};

/**
 * 검색 결과 클릭 후 요청하는 백준 랭크 정보 api
 * @param userIdParam 유저 아이디
 * @returns
 */
export const getSearchBojResult = async (userIdParam: number) => {
  const { data } = await authApi({
    method: 'get',
    url: `/boj/user-rank/${userIdParam}`,
  });

  return data;
};
