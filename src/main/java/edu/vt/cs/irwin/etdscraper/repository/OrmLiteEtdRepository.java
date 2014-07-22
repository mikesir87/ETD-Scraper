/*
 * File created on Jul 21, 2014 
 *
 * Copyright 2008-2013 Nerdwin15
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package edu.vt.cs.irwin.etdscraper.repository;

import java.sql.SQLException;

import javax.inject.Inject;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import edu.vt.cs.irwin.etdscraper.Etd;
import edu.vt.cs.irwin.etdscraper.EtdContributor;
import edu.vt.cs.irwin.etdscraper.domain.EtdContributorEntity;
import edu.vt.cs.irwin.etdscraper.domain.EtdEntity;
import edu.vt.cs.irwin.etdscraper.domain.EtdKeywordMapping;
import edu.vt.cs.irwin.etdscraper.domain.KeywordEntity;
import edu.vt.cs.irwin.etdscraper.domain.PersonEntity;

/**
 * An {@link EtdRepository} that uses ORMLite as the backing store.
 *
 * @author Michael Irwin
 */
public class OrmLiteEtdRepository implements EtdRepository {

  private Dao<EtdEntity, ?> etdDao;
  private Dao<PersonEntity, ?> personDao;
  private Dao<EtdContributorEntity, ?> contributorDao;
  private Dao<KeywordEntity, ?> keywordDao;
  private Dao<EtdKeywordMapping, ?> etdKeywordDao;

  @Inject
  public OrmLiteEtdRepository(ConnectionSource connectionSource) {
    try {
      this.etdDao = DaoManager.createDao(connectionSource, EtdEntity.class);
      TableUtils.createTableIfNotExists(connectionSource, EtdEntity.class);
      TableUtils.clearTable(connectionSource, EtdEntity.class);
      
      this.personDao = DaoManager.createDao(connectionSource, PersonEntity.class);
      TableUtils.createTableIfNotExists(connectionSource, PersonEntity.class);
      TableUtils.clearTable(connectionSource, PersonEntity.class);

      this.contributorDao = DaoManager.createDao(connectionSource, EtdContributorEntity.class);
      TableUtils.createTableIfNotExists(connectionSource, EtdContributorEntity.class);
      TableUtils.clearTable(connectionSource, EtdContributorEntity.class);
      
      this.keywordDao = DaoManager.createDao(connectionSource, KeywordEntity.class);
      TableUtils.createTableIfNotExists(connectionSource, KeywordEntity.class);
      TableUtils.clearTable(connectionSource, KeywordEntity.class);
      
      this.etdKeywordDao = DaoManager.createDao(connectionSource, EtdKeywordMapping.class);
      TableUtils.createTableIfNotExists(connectionSource, EtdKeywordMapping.class);
      TableUtils.clearTable(connectionSource, EtdKeywordMapping.class);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void saveEtd(Etd etd) {
    try {
      etdDao.createOrUpdate((EtdEntity) etd);
      for (EtdContributor contributor : etd.getContributors()) {
        if (contributor.getPerson().getId() == null)
          personDao.createOrUpdate((PersonEntity) contributor.getPerson());
        contributorDao.create((EtdContributorEntity) contributor);
      }
      
      for (EtdKeywordMapping mapping : etd.getKeywords()) {
        if (mapping.getKeyword().getId() == null)
          keywordDao.createOrUpdate(mapping.getKeyword());
        etdKeywordDao.createOrUpdate(mapping);
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
}
